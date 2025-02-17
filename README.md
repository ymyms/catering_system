# Catering System

## Proposal
***The Catering System app*** is designed to automate and efficiently manage the 
catering process for customers. It serves as a user-friendly 
platform for customers to browse menus, select food items, and place orders. 

***The primary users*** of this application will be customers and catering 
businesses. Customers will use the app to conveniently browse menus, select 
food items, and place orders. Catering businesses 
will utilize the app to efficiently check orders.

***The motivation*** behind this project is to create a more convenient tool for 
individual small food businesses, aiming to help them gain a clearer view 
of their business.

## User Stories:
*Customer*
- I want to be able to view a listing of dishes on the menu (include description, price and etc.)
- I want to be able to add a dish to my order
- I want to be able to see all the food items I have selected for my order
- I want to be able to remove a dish from my order
- I want to be able to place an order
- I want to edit, remove and add the existing dish (as a STAFF)
- I want to be reminded to save order and menu to file and have the option to do so or not. 
- I want to be given the option to load previous order and menu from file.

# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the admin access and then click the add dish button, by answering all the questions to add a dish successfully
- You can "remove multiple Xs from a Y" by clicking the admin access and then click the remove dish button, by answering dish name to remove a dish successfully
- You can "edit multiple Xs from a Y" by clicking the admin access and then click the edit dish button, by answering dish name to edit a dish's info successfully
- You can "filter multiple Xs from a Y" by clicking the admin access and then click the filter dish button, by select the category to filter dishes successfully
- You can locate my visual component by placing the order, there is a image display
- You can locate my visual component by seeing a splash screen appear in the beginning of the application 
- You can save the state of my application by clicking the save button
- You can reload the state of my application by clicking the load button

# Phase 4: Task 2
Events logged since application started:
- Fri Dec 01 12:06:43 PST 2023
Tteok-Bokki(dish) added to menu
- Fri Dec 01 12:06:43 PST 2023
Beef Tofu Soup(dish) added to menu
- Fri Dec 01 12:06:43 PST 2023
Cheese Cake(dish) added to menu
- Fri Dec 01 12:06:43 PST 2023
Coca Cola(dish) added to menu
- Fri Dec 01 12:07:12 PST 2023
Tea(dish) added to menu
- Fri Dec 01 12:07:20 PST 2023
Coca Cola(dish) removed from menu
- Fri Dec 01 12:08:38 PST 2023
Dish name edited from menu
- Fri Dec 01 12:08:38 PST 2023
Dish price edited from menu
- Fri Dec 01 12:08:38 PST 2023
Dish description edited from menu
- Fri Dec 01 12:08:38 PST 2023
Dish category edited from menu
- Fri Dec 01 12:08:47 PST 2023
Beef Tofu Soup(dish) added to order
- Fri Dec 01 12:08:48 PST 2023
Tea(dish) added to order
- Fri Dec 01 12:08:49 PST 2023
Bibimbap(dish) added to order
- Fri Dec 01 12:08:56 PST 2023
The order is placed.

# Phase 4: Task 3
Based on the UML diagram, if I have more time, the potential refactoring I will consider is to create separate classes for UI components and interactions, and another set of classes for managing the core functionalities, such as handling orders and updating the menu. Specifically, the current implementation of the "AdminWindow" and "ViewMenuWindow" classes handles both the UI and the underlying business logic. This separation could lead to a more extensible design, making it easier to maintain and extend the application in the future. Additionally, since my save and load operations need to contain both menu and order, they are currently not separable. If I have more time, I can implement options to only save or load the menu or order, providing more flexibility when needing an updated menu without previous order information.