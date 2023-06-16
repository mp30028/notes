#### java.lang.reflect.InaccessibleObjectException
Running junit5-tests throws the following exception.
![javaw_cJwledAI5F](https://user-images.githubusercontent.com/78896340/160387841-eab3f927-213a-44ff-b4d2-4e167c4187ac.png)

Fixed this by putting `--add-opens java.base/java.lang=ALL-UNNAMED` in run configurations of the test. 

(See Solution proposed on Stackoverflow)[https://stackoverflow.com/posts/68168810/revisions]

![javaw_rEZpgunmCE](https://user-images.githubusercontent.com/78896340/160388462-975a5f5b-4285-4441-8431-0c1964cb5e21.png)




[Back to Contents Page](./contents.md)
