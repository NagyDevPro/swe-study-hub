# Removing Stars From a String

*Best Practicies and issues for latest solution*
- Use of Stack: Your current solution uses a stack, which is a reasonable approach but can be simplified.

- Reversing and Iteration: You are iterating from the end of the string and reversing it, which can make the logic a bit more complex and harder to follow.

- Memory Usage: The use of a stack increases memory usage. You can reduce the space complexity to O(1) by using a string directly.

* Improved Solution: Instead of using a stack, you can use a single string as a result builder. This approach is easier to understand and avoids unnecessary space usage.


-----------------------------------------------------------------------------





TODO List

0- add reactions with state of a reaction -done
0- add releations between comment, like and post and user -done
0- add cache layer for likes and dislikes(simply use local variables) -done
0- exception handling -done
3- build login ui
4- build user component
5- build posts component
1- run your application with mapstruct
2- if map struct fails, use model mapper

6- swagger
7- containerize your application
8- make a container for the frontend
9- make database scheme, and documentation



0- seprate liquibase changelog to multi -done


- show wether the current user is reacted to the post or not
- return the username and user profile image in the comment dto
- add existing apis for user like:
  - get user details
  - get user profile
  - edit user profile
- when returning all of the posts --> return post details instead of postDto
- enahnce fetch type to avoid n+1 problems


- think better in this feature and asses on it, when a user requests all the posts i want to show within each post wether if the current user is already reacted to this post or not
(please assess well and tell me what's the best practice for implemeting it)


- add pagination


---
not mandatory
1- like and dislike to a comment
2- admin dashboard
3- upload image
4- twitter and single sign on




