# NewsApp

## Business Requirements

### 1. User Registration Endpoint
Allows new users to securely register, providing essential details for account creation.

### 2. User Authentication Service

Facilitates user login with validation of credentials and returns a JWT token.
### 3. Role Management System

Offers APIs for administrators to create, read, update, and delete user roles.
### 4. Article Creation Feature

Enables editors to create and publish new articles with content management tools.
### 5. Article Modification Service

Provides functionality for editing and deleting existing articles to keep content updated.
### 6. Article Retrieval and Listing

An API for accessing specific articles by ID and listing all available articles.
### 7. Admin-Controlled Category Management

An API set for administrators to manage article categories, enhancing content organization.
### 8. Comment Posting Functionality

Allows registered users to post comments on articles, enhancing community interaction.
### 9. Comment Management Service

Enables users to edit or delete their comments, promoting responsible discussions.
### 10. Selective User Authorization

Implements a robust authorization system for admins and regular users managing special functionalities and commenting rights.


## MVP Features
### 1. User Registration and Authentication

   Implement user registration and authentication endpoints to allow users to create accounts and securely log in. Utilize basic user information such as username, email, and password.

### 2. Article Management

   Create APIs for creating, updating, viewing, and deleting articles. Authors should be able to create articles with titles and content, edit and delete articles.

### 3. User Role Management

   Develop APIs for basic user role management. Admins should have the ability to create, read, update, and delete roles. Define at least two roles, such as "User" and "Admin."

### 4. Comment Posting and Management

   Implement the ability for registered users to post comments on articles. Provide endpoints for posting comments, editing own comments, and deleting own comments.

### 5. User Authorization

   Set up role-based access control to restrict certain actions based on user roles. Admins should have privileged access, while regular users can perform basic actions like commenting. Everyone, including guests, should be able to view articles and comments.