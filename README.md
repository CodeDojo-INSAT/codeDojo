## Getting Started

Welcome to the CodeDojo.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `WEB-INF/classes` folder by default.

## Available api's

- Daily questions Api's
    - /services/submit_answer.dojo
    - /services/get_userstreak.dojo
    - /services/post_question.dojo
    - /services/get_question.dojo

- Main app api's
    - /services/get_question_description.dojo
    - /services/check_answer.dojo
    
- Auth api's
    - /auth/home
    - /auth/signup
    - /auth/logout
    - /services/login.dojo
    - /services/signup.dojo
    - /services/generate_otp.dojo
    - /services/verify_otp.dojo

- Admin api's
    - /services/upload_course_questions.dojo
    - /services/update_course_questions.dojo
    - /services/fetch_course_questions.dojo
    - /services/fetch_levels.dojo
    - /services/fetch_checker.dojo