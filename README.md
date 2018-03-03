# botl-comment-lambda
Lambda function to hold add comment code for botl project

# Setup
## Building 
*Just use eclipse*
## Lambda
- Create a new [aws lambda](https://aws.amazon.com/lambda/) function
  - Name it something useful like **botl-comment-app**
  - Set runtime to **Java 8**
  - Create a new role *(or use existing botl database role)*
- Add environment variables
  - BOTL_DATABASE_PASSWORD  **TODO add encryption?**
  - BOTL_DATABASE_USERNAME
  - BOTL_DATABASE_ENDPOINT
  - BOTL_DATABASE_PORT
- Set timeout to something like 3 mins*?*
- Add **API Gateway** trigger
  - Create new API named something like **botl-rest-api** *(or use existing botl rest api)*
  
  
## API Gateway
- Actions > Create Method > POST
  - Intergration type -> Lambda Function
  - Lambda Region should be same as in your lambda function (See aws top bar)
  - Select your lambda function
- Actions > Enable CORS
- In the POST request method execution screen, select the **Intergration Request** menu
  - Body Mapping Templates > When there are no templates defined (recommended)
  - add mapping template 
  - use **application/x-www-form-urlencoded**
```javascript
~~{~~
~~    "comment" : "$input.params('comment')",~~
~~    "application" : "$input.params('application')",~~
~~    "name" : "$input.params('name')",~~
~~    "email" : "$input.params('email')"~~
~~}~~
{
    "body": "$input.body"
}
```

## Botl Database Role
Role needs 
- AmazonRDSReadOnlyAccess
- AWSLambdaVPCAccessExecutionRole