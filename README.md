## Getting Started

Welcome to the CodeDojo.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `WEB-INF/lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `WEB-INF/classes` folder by default.

## Available api's

- Daily questions Api's
    - /services/dq/submit_answer.dojo `[POST]`
    - /services/dq/get_userstreak.dojo `[GET]`
    - /services/dq/get_question.dojo `[GET]`

- Course api's
    - /services/course/get_course_question.dojo `[GET]`
    - /services/course/check_answer.dojo `[POST]`
    
- Auth api's
    - /auth/home 
    - /auth/signup
    - /auth/logout
    - /services/auth/login.dojo `[POST]`
    - /services/auth/signup.dojo `[POST]`
    - /services/auth/generate_otp.dojo `[GET]`
    - /services/auth/verify_otp.dojo `[GET]`

- Admin api's
    - /services/admin/course/upload_course_questions.dojo `[POST]`
    - /services/admin/course/update_course_questions.dojo `[POST]`
    - /services/admin/course/fetch_course_questions.dojo `[GET]`
    - /services/admin/course/fetch_levels.dojo `[POST]`
    - /services/admin/course/fetch_checker.dojo `[GET]`

    - /services/admin/dq/post_question.dojo `[POST]`
    - /services/admin/dq/update_testcase.dojo `[POST]`
    - /services/admin/dq/fetch_testcase.dojo `[GET]`
    - /services/admin/dq/update_question.dojo `[POST]`
    - /services/admin/dq/fetch_titles.dojo `[GET]`
    - /services/admin/dq/delete_question.dojo `[GET]`