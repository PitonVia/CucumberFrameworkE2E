Feature: Submit data to webdriveruniversity.com using contact us form
	A user should be able to submit information via the contact us form

Scenario: Submit information using the contact us form
	Given I access webdriveruniversity and click on the contact button
	When I enter a valid first name
	And I enter a valid last name
	|woods|jackson|jones|
	And I enter a valid email address
	And I enter comments
	|example comment 1|example comment 2|
	When I click on the submit button
	Then The information should successfully be submitted via the contact us form

