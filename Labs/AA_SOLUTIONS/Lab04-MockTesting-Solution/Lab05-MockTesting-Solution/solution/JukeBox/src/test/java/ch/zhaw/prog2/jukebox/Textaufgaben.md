
    
## 1.2a)  -> Textaufgabe
    
White-Box testing allows some insight into the code logic, possible
paths. -> exhaustive path testing -> coverage tests
-> MockTests and coverage tests

Black-Box testing allows no insight into the logic
-> exhaustive input testing -> UnitTest 


## 1.2c) -> Textaufgabe


Sometime the coupling between two classes is very high,
which makes it difficult to test the classes independently.
In most cases this happens, if classes are creating it's
dependent instances themself using new.  
Solution: Use of Dependency Injection.
In dependency Injection, the dependent instances to be used
are configured (injected) when initializing the class.
This makes it possible to inject a mock-object instead of the
real object in unit tests.