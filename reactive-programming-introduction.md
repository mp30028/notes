## Introductory notes on reactive programming in Java

### General Concepts
#### Overview
![image](https://user-images.githubusercontent.com/78896340/159191166-05454347-045b-47c1-b406-186c1b185dd6.png)

#### Operators
![image](https://user-images.githubusercontent.com/78896340/159191513-fe7dd519-9a63-487d-93ce-c5a391afd546.png)



#### Four Key Interfaces
1. **Publisher** (read-only)
2. **Subscriber** (write-only)
3. **Subscription**
4. **Operartor** (act as both subscriber and publisher)

#### Subscriber Interface
Four Methods for reacting to situations (Events) as and when they occur
![image](https://user-images.githubusercontent.com/78896340/159192011-9947c6af-7e84-4f92-aced-6cea4b2bfe2c.png)


#### Publisher Types:
There are 2 types of Publishers

##### Cold Publisher
- Lazy. Starts producing/emitting only when a subscriber subscribes
- Publisher creates a data producer and generates new sets of values for each new subscription
- When there are multiple observers, each observer might get different values

##### Hot Publisher
- Values are generated outside the publisher even when there are no observers.
- There will be only one data producer
- All the observers get the value from the single data producer irrespective of the time they started subscribing to the publisher. It means any new observer might not see the old value emitted by the publisher.

### Mono and Flux
Mono and Flux are implementations of the Publisher Interface.
- **Mono**: emits 0 or 1 element.
- **Flux**: emits 0 or N element(s). (Potentially unbounded)
- Possible to terminate with either **onComplete** or an **onError** events. 
- Completely asynchronous. Only emit values when there are downstream subscribers


### Subscribing to a producer

![image](https://user-images.githubusercontent.com/78896340/159404685-23b716cd-9fc2-4f3a-bf63-ae036d60fed4.png)



### Web Resources
1. [Introduction](http://www.vinsguru.com/reactive-programming-a-simple-introduction/)
2. [Creating Streams — Flux vs Mono](https://www.vinsguru.com/mono-vs-flux-project-reactor/)
3. [Reactor Hot Publisher vs Cold Publisher](https://www.vinsguru.com/reactor-hot-publisher-vs-cold-publisher/)
4. [Flux Create vs Flux Generate](https://vinsguru.medium.com/java-reactive-programming-flux-create-vs-flux-generate-38a23eb8c053)
5. [Combining Multiple Sources Of Flux / Mono](https://www.vinsguru.com/reactive-programming-reactor-combining-multiple-sources-of-flux-mono/)
