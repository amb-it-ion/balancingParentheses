#balancingParentheses
====================

## Puzzle
In February 2014 I was given a coding puzzle. Stated was a set of rules detailing the balancing parenthesis problem: Given an expression string exp, write a program to examine whether the pairs and the orders of its brackets are correct in exp.

### Correctness
here is defined as: 

1 Brackets can be round "()", curly "{}" or squared "[]"
2 Each type of bracket needs to be first opened then closed
3 You can only close the last bracket that was opened
4 Inside round brackets only curly brackets are allowed
5 Inside curly brackets only square brackets are allowed
6 Any bracket can appear (directly) inside square brackets
7 You can use a list of brackets where a single one is allowed of that type!
8 An empty string is not valid expression

### Quality attributes

1 Clean code
2 Reasonable design &
3 Reasonable performance

### IO

+ Input is STDIN
+ Output is STDOUT

## Solution

For organisational reasons I have put my solution on github.com, so whoever is interested (most likely my possible employer) can take a look.

### Requirements
I have started with Maven 2 Java 6/7, and finished with Maven 3 and Java 8 SDK, using the latest Eclipse Kepler build and a Java 8 beta extension. This will make parallelism both simple to read and fun to develop. Spring 4 is used for demonstration purpose and elegant mock testing. Spring Bootstrap adds a little annotational automagicion.

### Architectural Decisions

I have followed the quality attributes:
#### Clean Code
means to me, that the code is readable, tested and ready for change. For more information, please read Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)
#### Reasonable design
follows the need for change readiness. My reasoning is, that this kind of parentheses balancing is highly customised using rules. Following this unusial nature, I believe the design should focus on allowing to change those rules easily.
#### Reasonable performance
was a tough one. While I love Java, I believe that if you really want to go for performance, a machine-closer approach would be more promising. Secondly, performance in this task was defined also in terms of parallel scaling. Unfortunately the optimal performance for this tasks depends heavily on the input data, e.g. technically its access delay, buffer size, but also content-wise its brackets depth, input length, etc. Depending on those, different strategies will have very different results in performance. However a decision had to be made, and to stay consistent with my previous understanding, I have decided to scale across rules - the only really unique feature about this task. Lastly, I try to use the smallest possible data structures available to represent the task, thus saving memory. 

### Code Highlights

#### Dynamic Declarative Rule Generation
allows very easy adjustment to the rule basis using a nicely readable builder pattern
#### Hashed Rule Pipelining
means that no rule is called on a symbol it's not interested in

### TODO
+ Code TODOs
+ Test coverage
+ API Comments coverage
+ Refactor Rule Builder
+ Find a better solution for null char, taking into account memory http://www.javaworld.com/article/2076571/java-se/an-in-depth-look-at-java-s-character-type.html