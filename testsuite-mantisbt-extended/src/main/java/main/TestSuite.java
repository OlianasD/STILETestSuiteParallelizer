package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.*;

@RunWith(Suite.class)
@SuiteClasses({

		AddUserTest.class,
		AddUserWrongTest.class,
		AddUserEmptyTest.class,	
		AddProjectTest.class,	
		AddProjectWrongTest.class,
		AddProjectEmptyTest.class,
		AddCategoryTest.class,
		AddCategoryWrongTest.class,
		AddCategoryEmptyTest.class,	
		AddIssueTest.class,
		AddIssueEmptyTest.class,
		AssignIssueTest.class,
		ViewSummaryIssueTest.class,
		UpdateIssuePriorityTest.class,
		UpdateIssueSeverityTest.class,
		UpdateIssueStatusNewTest.class,
		UpdateIssueStatusFeedbackTest.class,
		UpdateIssueStatusAcknowledgedTest.class,
		UpdateIssueStatusConfirmedTest.class,
		UpdateIssueStatusAssignedTest.class,
		UpdateIssueStatusResolvedTest.class,
		UpdateIssueSummaryTest.class,
		UpdateProjectDescriptionTest.class,
		UpdateProjectStatusTest.class,
		UpdateProjectViewStatusTest.class,	
		UpdateCategoryTest.class,
		UpdateCategoryEmptyTest.class,
		UpdateUserTest.class,
		UpdateUserEmptyTest.class,		
		DeleteCategoryTest.class,
		DeleteIssueTest.class,
		DeleteProjectTest.class,
		DeleteUserTest.class,
		LoginNegativeTest.class,
		LogoutTest.class,
		AddMultipleUsersTest.class,
		UpdateUser1AccessLevelReporterTest.class,
		UpdateUser1AccessLevelUpdaterTest.class,
		UpdateUser1AccessLevelDeveloperTest.class,
		UpdateUser2AccessLevelUpdaterTest.class,
		UpdateUser2AccessLevelManagerTest.class,
		UpdateUser2AccessLevelAdministratorTest.class,
		UpdateUser3AccessLevelDeveloperTest.class,
		UpdateUser3AccessLevelUpdaterTest.class,
		UpdateUser3AccessLevelReporterTest.class,
		DeleteMultipleUsersTest.class,
		AddMultipleSubprojectsTest.class,
		UnlinkMultipleSubprojectsTest.class,
		LinkMultipleSubprojectsTest.class,
		AddCategoryToSubproject1Test.class,
		AddVersionToSubproject1Test.class,
		UpdateSubproject1StatusReleaseTest.class,
		UpdateSubproject1StatusStableTest.class,
		UpdateSubproject1StatusObsoleteTest.class,
		UpdateSubproject1ViewStatusTest.class,
		UpdateSubproject1DescriptionTest.class,
		AddCategoryToSubproject2Test.class,
		AddVersionToSubproject2Test.class,
		UpdateSubproject2StatusReleaseTest.class,
		UpdateSubproject2StatusStableTest.class,
		UpdateSubproject2StatusObsoleteTest.class,
		UpdateSubproject2ViewStatusTest.class,
		UpdateSubproject2DescriptionTest.class,
		DeleteMultipleSubprojectsTest.class
		
})

public class TestSuite {
}
