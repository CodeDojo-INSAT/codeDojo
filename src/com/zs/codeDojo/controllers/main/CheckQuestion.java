package com.zs.codeDojo.controllers.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.zs.codeDojo.models.DAO.DBModule;
import com.zs.codeDojo.models.DAO.IOStreams;
import com.zs.codeDojo.models.DAO.JsonResponse;
import com.zs.codeDojo.models.DAO.TestCases;
import com.zs.codeDojo.models.DAO.User;
import com.zs.codeDojo.models.checkTestCases.CheckLogic;
import com.zs.codeDojo.models.checkTestCases.Loader;
import com.zs.codeDojo.models.main.JavaFileCompile;

public class CheckQuestion extends HttpServlet {
    private PrintWriter writer = null;
    private JSONObject jsonObject = null;
    private ErrorList compilationError = null;
    private Matcher matcher = null;
    private Pattern pattern = null;
    private String javaCodeString;
    private int level;
    private JavaFileCompile compiler = null;
    private JsonResponse jsonResponse = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request);
        this.writer = response.getWriter();

        setResponseHeader(response);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String className = findMainClass(javaCodeString);

        if (className == null) {
            JSONObject json = new JSONObject();
            json.put("error","Class name Must be Capital case");
            json.put("type", "oops");
            jsonResponse = new JsonResponse(false, "main class not found", json);
            response.getWriter().print(jsonResponse);
            return;
        }

        ServletContext context = getServletContext();

        DBModule dbModule = (DBModule) context.getAttribute("db");

        int type = dbModule.getTypeOfQuestion(level);
        jsonObject.clear();

        if (type == 0) {

            // writer.write(jsonObject.put("error", "this question doesn't have any checkers
            // related").toString());
            
            jsonResponse = new JsonResponse(false, "Question doesn't have relate to any checker", null);
            writer.print(jsonResponse);
        } else if (type == 5) {

            TestCases testCases = dbModule.getTestCases(level);

            if (testCases != null) {
                Loader loader = new Loader(javaCodeString);
                Class<?> clazz = loader.compileAndLoadClass();

                if (clazz == null) {
                    // jsonObject.put("compilationError", loader.getError());
                    jsonResponse = new JsonResponse(false, "compilation error", loader.getError());
                } else {
                    CheckLogic checker = new CheckLogic(clazz, testCases, (IOStreams) context.getAttribute("streams"));

                    // jsonObject.put("res", checker.getResult());
                    JSONObject json = new JSONObject();
                    if (checker.hasError()) {
                        json.put("error", checker.getError());
                        jsonResponse = new JsonResponse(false, "execution time out", json);
                    }
                    else {   
                        json.put("result", checker.getResult());
                        json.put("sampleTestcase", testCases.getSampleTestCase());
                        
                        if (!checker.isMatched()) {
                            json.put("user_output", checker.getMessage());
                            jsonResponse = new JsonResponse(false, "testcases not matched", json);
                        } else {
                            jsonResponse = new JsonResponse(true, "all testcases passed", json);
    
                            User user = (User)request.getSession().getAttribute("user");
                            int currentLevelofUser = (dbModule.getCurrentLevel(null));
    
                            if (currentLevelofUser == level) {
                                dbModule.updateCurrentLevel(user);
                                dbModule.addSubmission(user.getUsername(),level,javaCodeString);
                            }
                            else{
                                dbModule.updateSubmission(user.getUsername(),level,javaCodeString);
                            }
                        }
                    }

                }
            } else {
                // jsonObject.put("error", "it doesn't have any testcases");
                jsonResponse = new JsonResponse(false, "question doesn't have any testcases", null);

            }
            writer.print(jsonResponse);
        } else {
            (compiler = new JavaFileCompile(className, javaCodeString, (compilationError = new ErrorList())))
                    .compileJava();

            jsonObject.clear();
            ErrorList error = new ErrorList();

            if (compiler.getStatus()) {
                CompilationUnit compilationUnit = analyzeCode(javaCodeString);
                String checkerName = dbModule.getClassNameOfType(type);

                handleCompilationUnit(compilationUnit, checkerName, error);
            } else {
                handleCompilationError();
            }
        }

        writer.close();
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, level");
    }

    private void processRequest(HttpServletRequest request) throws IOException {
        String content = "";
        BufferedReader reader = request.getReader();

        String line;
        while ((line = reader.readLine()) != null) {
            content += line;
        }

        jsonObject = new JSONObject(content);

        javaCodeString = jsonObject.getString("code");
        level = jsonObject.getInt("level");
    }

    private CompilationUnit analyzeCode(String code) {
        CompilationUnit compilationUnit = null;

        if (code != null && code.length() > 1) {
            ParseResult<CompilationUnit> parseResult = new JavaParser().parse(code);

            if (parseResult.isSuccessful()) {
                compilationUnit = parseResult.getResult().get();
            }
            // else {
            // handleParseError(parseResult);
            // }
        } else {
            (compilationError = new ErrorList()).add("code should not be empty!!");
        }

        return compilationUnit;
    }

    // private void handleParseError(ParseResult<CompilationUnit> parseResult) {
    // pattern = Pattern.compile("line \\d{1,}, column \\d{1,}");

    // compilationError = new ErrorList();
    // parseResult.getProblems().forEach(problem -> {
    // matcher = pattern.matcher(problem.getCause().toString());

    // if (matcher.find())
    // compilationError.add(matcher.group());
    // else
    // compilationError.add("Something went wrong... Check code.");
    // });
    // }

    private void handleCompilationError() {
        jsonObject.put("data", compilationError.toString());
        jsonObject.put("message", "compilation error");
        jsonObject.put("status", false);
        writer.println(jsonObject.toString());
    }

    private void handleCompilationUnit(CompilationUnit compilationUnit, String checkerName, ErrorList error) {
        // LevelHandler handler = levelHandlers.get(level);
        // if (handler != null)
        // writeData(handler.handle(compilationUnit, error), error);
        String checkerPackage = "com.zs.codeDojo.models.checkers.";
        try {
            Class<?> clazz = Class.forName(checkerPackage + checkerName);

            Object instance = clazz.getDeclaredConstructor(ArrayList.class).newInstance(error);

            compilationUnit.accept((VoidVisitor<?>) instance, null);
            Field statusField = clazz.getDeclaredField("status");

            boolean status = (boolean) statusField.get(instance);

            JSONObject json = new JSONObject();
            json.put("type", "oops");

            if (status) {
                jsonResponse = new JsonResponse(status, "oops checked", json);
            } else {
                json.put("error", error.toArray());
                jsonResponse = new JsonResponse(status, "can't check oops by checker", json);
            }
            writer.print(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private void writeData(boolean status, ErrorList errorList) {
    // jsonObject.put("status", status);
    // if (!status)
    // jsonObject.put("error", errorList.toString());

    // writer.println(jsonObject.toString());
    // }

    private String findMainClass(String code) {
        String mainClassName = null;
        pattern = Pattern.compile("(?<=public class )[A-Z][a-zA-Z]*");

        matcher = pattern.matcher(code);

        if (matcher.find()) {
            mainClassName = matcher.group();
        }

        return mainClassName;
    }
}

class ErrorList extends ArrayList<String> {
    public ErrorList() {
        super();
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.size(); i++) {
            if (i == this.size() - 1)
                res += "" + this.get(i) + "";
            else
                res += "" + this.get(i) + ",";
        }
        res += "";
        return res;
    }
}