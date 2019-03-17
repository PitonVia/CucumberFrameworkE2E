Feature: Products page

Scenario: validate promo code alert is visible when clicking on the special offers link
	Given user goes to "http://www.webdriveruniversity.com/Page-Object-Model/products.html" website
	When user clicks on Special Offers button
	Then user should be presented with a promo alert
	
