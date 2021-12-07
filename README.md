# log-anonymizer
log anonymizer 


1. List the observations and assumptions you made about the log file (3-5 sentences)
Each line contains Date of Birth token which starts with DOB
2. What are some limitations of your solution and how would you fix them? (3-5 sentences)
3. What steps did you take to verify your solution works as intended? (3-5 sentences)
 Confirmed with postman testing. Ideally, I would write some unit test and integration test if I have more time.
4. How would you restructure your solution on a systems level to handle a high volume of very large log files, e.g. millions of lines? (short paragraph)
 I would use stream processing to parse and update content of file. Streams comes with inbuilt parallel stream which uses fork join pool to concurrent process stream contents. 
5. Additionally, how would your testing process differ for a production-ready project? (short paragraph)
I would also perform performance and load test other than integration and end to end test.
