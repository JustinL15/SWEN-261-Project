---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information

* Team name: TEAMNAME
* Team members
  * Jessica Eisler
  * Matt London
  * Alex Pross
  * Justin Lin
  * Alexis Sanders

## Executive Summary

This is a summary of the project.

### Purpose

> _**[Sprint 2 & 4]** Provide a very brief statement about the project and the most
> important user group and user goals._
>
> Creating a e-store that sells coffee related products. The customers are the 
>
> important users and they should be able to effectivley use the store. That is
>
> viewing, adding and purchasing products
>

### Glossary and Acronyms

> _**[Sprint 2 & 4]** Provide a table of terms and acronyms._

| Term | Definition                |
| ---- | ------------------------- |
| SPA  | Single Page               |
| DAO  | Data Access Object        |
| CSS  | Cascading Style Sheets    |
| TS   | Type Script               |
| HTML | Hypertext Markup Language |

## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
This server will allow users to login with a simple username. 
When the username entered is "admin" then the user should be able to edit items within the inventory
Any user that is not an admin should be able to see a list of products, search for a specific product, edit a shopping cart, and checkout.
The data stored within the shopping carts and the inventory should be saved and accessible after logging out and back into an account. 

> _**[Sprint 2 & 4]** Provide a simple description of the Minimum Viable Product._

### MVP Features

> _**[Sprint 4]** Provide a list of top-level Epics and/or Stories of the MVP._

### Enhancements

> _**[Sprint 4]** Describe what enhancements you have implemented for the project._

## Application Domain

This section describes the application domain.

![Domain Model](DomainAnalysis.png)

The main interactions within our domain model take place through a customer's interactions with the store. <br>
A **customer** will create an account and then browse the store's **inventory** which will display a collection of **product**. <br>
The user can view existing **review** for each **product**, view **product** sorted by **category** as well as receive a **recommendation** for other products, this assists the user in making purchases. <br>
Once the **customer** has decided to purchase a **product** they can add the item to their **cart** and continue to add more items until they are ready to purchase. <br>
When purchasing, their **cart** will be moved to the **checkout** phase and all items will be marked as purchased and saved in an **order**. <br>
The **customer** can then view the status of their **order**. <br>
The **owner** can mark an **order** as complete, which will notify the **customer** that their order has been processed.

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

The e-store web application, is built using the Model–View–ViewModel (MVVM) architecture pattern.

The Model stores the application data objects including any functionality to provide persistance.

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.

### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the e-store application.

> _Provide a summary of the application's user interface.  Describe, from
> the user's perspective, the flow of the pages in the web application._

### View Tier

> _**[Sprint 4]** Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _**[Sprint 4]** You must  provide at least **2 sequence diagrams** as is relevant to a particular aspects
> of the design that you are describing.  For example, in e-store you might create a
> sequence diagram of a customer searching for an item and adding to their cart.
> As these can span multiple tiers, be sure to include an relevant HTTP requests from the client-side to the server-side
> to help illustrate the end-to-end flow._

> _**[Sprint 4]** To adequately show your system, you will need to present the **class diagrams** where relevant in your design. Some additional tips:_
>
> * _Class diagrams only apply to the **ViewModel** and **Model** Tier_
> * _A single class diagram of the entire system will not be effective. You may start with one, but will be need to break it down into smaller sections to account for requirements of each of the Tier static models below._
> * _Correct labeling of relationships with proper notation for the relationship type, multiplicities, and navigation information will be important._
> * _Include other details such as attributes and method signatures that you think are needed to support the level of detail in your discussion._

### ViewModel Tier

> _**[Sprint 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._

![Replace with your ViewModel Tier class diagram 1, etc.](model-placeholder.png)

### Model Tier
The Model Tier consists of classes that represent our objects within the code. They are leveraged in the DAOs in order to save their data and keep persistence. They are also used by the controllers to process API requests.

#### Product
The product model represents an item that exists within the inventory. It tracks quantity and other information specific to the item.
![Product model](productModel.png)

#### Customer
The customer model allows representing a user and tying them to their cart and all existing orders. It also holds a password to allow login information.
![Customer model](customerModel.png)

#### Cart
The cart model saves a customer's cart so that it appears on consecutive logins. It stores a collection of products and their quantities. It is cleared once the checkout phase is completed.
![Cart model](cartModel.png)

#### Order
The order model saves a snapshot of products at the time of purchase as well as computes a total. This allows price changes in the future that will not impact past orders. A customer is tied to an order using the order ID.
![Order model](orderModel.png)

## OO Design Principles

**Information Expert** states the behavior of a class or object should have functions to interpret its own data and not have to pass around its own data to different objects in order to parse the final meaning. Adhering to the principle results in programs that have low coupling and are less complex.
The current ProductFileDAO class has the methods to create, delete, get, update, and search for products because it has the name of the file we are storing the product data in and thus has an easier time accessing the data.
![Information Expert Implementation Example](information-expert-example.png)

**Controller** represents the user functions. It is a non-user interface that handles the system events. It breaks up the work done by other modules and it controls object activity. It provides a basis to then be performed by other objects within the project.
A controller can be used in our implementations of owner product control, customer shopping cart, custom reviews, customer recommendations, and the controller responsible for logging on to the website itself.
![Controller Implementation Example](controller-example.png)

**Low coupling**

> Modles only call on others when needed. Information is only passed when neccisary to accomplish other tasks. This prevents too much dependency among the modules. A good example is the AuthGuard hooking to the Owner View. This allows it to ensure that only owners can view specific parts of the site and easily allows that access.

![1679358202811](image/DesignDoc/1679358202811.png)

**Single responsibility**

> Each class has one purpose. To achieve other purposes it calls on other classes. The ts calls upon the DAOs in order to create, update, delete etc. this ensures that the code is not only reusable but also not hard coded and can change with each need. Products is a good example because they are all connected but do not do multiple things. These then get called in the ts files to use the functions. 

![1679358538861](image/DesignDoc/1679358538861.png)


> _**[Sprint 3 & 4]** OO Design Principles should span across **all tiers.**_

## Static Code Analysis/Future Design Improvements

> _**[Sprint 4]** With the results from the Static Code Analysis exercise,
> **Identify 3-4** areas within your code that have been flagged by the Static Code
> Analysis Tool (SonarQube) and provide your analysis and recommendations.
> Include any relevant screenshot(s) with each area._

> _**[Sprint 4]** Discuss **future** refactoring and other design improvements your team would explore if the team had additional time._

## Testing

> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing

> _**[Sprint 2 & 4]** Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage

> _**[Sprint 4]** Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets._

>_**[Sprint 2 & 4]** **Include images of your code coverage report.** If there are any anomalies, discuss
> those._
