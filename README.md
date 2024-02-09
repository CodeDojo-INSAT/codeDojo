## Getting Started

Welcome to the CodeDojo.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `WEB-INF/classes` folder by default.

## Available api's

- Daily questions Api's
    - /services/dq/submit_answer.dojo
    - /services/dq/get_userstreak.dojo
    - /services/dq/get_question.dojo

- Course api's
    - /services/course/get_course_question.dojo
    - /services/course/check_answer.dojo
    
- Auth api's
    - /auth/home
    - /auth/signup
    - /auth/logout
    - /services/auth/login.dojo
    - /services/auth/signup.dojo
    - /services/auth/generate_otp.dojo
    - /services/auth/verify_otp.dojo

- Admin api's
    - /services/admin/course/upload_course_questions.dojo
    - /services/admin/course/update_course_questions.dojo
    - /services/admin/course/fetch_course_questions.dojo
    - /services/admin/course/fetch_levels.dojo
    - /services/admin/course/fetch_checker.dojo

    - /services/admin/dq/post_question.dojo
    - /services/admin/dq/update_testcase.dojo
    - /services/admin/dq/fetch_testcase.dojo
    - /services/admin/dq/update_question.dojo
    - /services/admin/dq/fetch_titles.dojo
    - /services/admin/dq/delete_question.dojo