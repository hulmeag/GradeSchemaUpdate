# GradeSchemaUpdate
Run:

java -jar UltraCopySchmea.jar

Enter File name:
c:/temp/courselist.txt

Enter User:
**************************************
Enter Secret :
**************************************
Enter URL (https://ultra-magic.blackboard.com/):
https://ultra-magic.blackboard.com/
Course to copy Grade Schema From:
origincourse
https://support-ultra-test.blackboard.com/learn/api/public/v1/courses/courseId:origincourse/gradebook/schemas


Getting current Schema for course: origincourse JSON Record {"scaleType":"Tabular","id":"_3114_1","title":"Decimal","symbols":[{"absoluteValue":100,"upperBound":100,"lowerBound":95,"text":"10"},{"absoluteValue":71.25,"upperBound":95,"lowerBound":47.5,"text":"9"},{"absoluteValue":23.75,"upperBound":47.5,"lowerBound":0,"text":"0"}]}


Origin Course JSON: {"scaleType":"Tabular","id":"_3114_1","title":"Decimal","symbols":[{"absoluteValue":100,"upperBound":100,"lowerBound":95,"text":"10"},{"absoluteValue":71.25,"upperBound":95,"lowerBound":47.5,"text":"9"},{"absoluteValue":23.75,"upperBound":47.5,"lowerBound":0,"text":"0"}]}


Using Token: nadSCmekHjMeM3oG6c42FGgJPyBx70Y4


https://ultra-magic.blackboard.com/learn/api/public/v1/courses/courseId:course1/gradebook/schemas


Getting current Schema for course: course1 JSON Record {"scaleType":"Tabular","id":"_3088_1","title":"Decimal","symbols":[{"absoluteValue":100,"upperBound":100,"lowerBound":95,"text":"10"},{"absoluteValue":71.25,"upperBound":95,"lowerBound":47.5,"text":"9"},{"absoluteValue":23.75,"upperBound":47.5,"lowerBound":0,"text":"0"}]}


Patch Course course1 Updating JSON to: {"id":"_3088_1","title":"Decimal","scaleType":"Tabular","symbols":[{"text":"10","absoluteValue":100.00000,"lowerBound":95.00000,"upperBound":100.00000},{"text":"9","absoluteValue":71.25000,"lowerBound":47.50000,"upperBound":95.00000},{"text":"0","absoluteValue":23.75000,"lowerBound":0.00000,"upperBound":47.50000}]}


Using Token: nadSCmekHjMeM3oG6c42FGgJPyBx70Y4


https://ultra-magic.blackboard.com/learn/api/public/v1/courses/courseId:course2/gradebook/schemas


Getting current Schema for course: course2 JSON Record {"scaleType":"Tabular","id":"_3093_1","title":"Decimal","symbols":[{"absoluteValue":100,"upperBound":100,"lowerBound":95,"text":"10"},{"absoluteValue":71.25,"upperBound":95,"lowerBound":47.5,"text":"9"},{"absoluteValue":23.75,"upperBound":47.5,"lowerBound":0,"text":"0"}]}


Patch Course course2 Updating JSON to: {"id":"_3093_1","title":"Decimal","scaleType":"Tabular","symbols":[{"text":"10","absoluteValue":100.00000,"lowerBound":95.00000,"upperBound":100.00000},{"text":"9","absoluteValue":71.25000,"lowerBound":47.50000,"upperBound":95.00000},{"text":"0","absoluteValue":23.75000,"lowerBound":0.00000,"upperBound":47.50000}]}
