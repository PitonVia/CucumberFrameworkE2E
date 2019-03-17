@login
Feature: Login into account
	Existing user should be able to login to his account using correct credentials

Scenario: Login into account with correct credentials
Given User navigates to the StackOverflow website
And User clicks on the Login button on Home page
And User enters a valid username
And User enters a valid password
When User clicks on the Login button
Then User should be taken to the successful Landing page