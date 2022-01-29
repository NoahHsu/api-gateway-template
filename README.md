# API Gateway template

## Feature
- Spring Component logging
- Example RESTful API Client
  - CustomRetryCallUtils: can retry on both Response status and custom condition of Response body
- Example RESTful API Controller
- Filter for auth
  - FilterConfig.java: setting which path should go through which Filter
  - BodyAuthFilter: Example filter for auth-token in header verify using request body
- Input Validation
  - model/xxx.java: using javax.validation.constraints annotaion to validate input. if fail then throw exception.
- Error Handling
  - ControllerAdviceConfig: unify logic of catch Exception and response http status
- Basic Encrypttion Utils
- code quality examine
  - error prone(bug and code smell)
  - jacoco(test coverage)
## Demo
  ```shell
  gradle bootRun
  ```

## Configuration
After copy from template, we need to modify some config to become a new project
- replace all `org.example.api.gateway.template` in folder structure
- replace package name in all java file
- in `build.gradle`
  - replace group
  - replace classDirectories in fileTree fro jacoco report
- replace rootProject.Name in `setting.gradle`