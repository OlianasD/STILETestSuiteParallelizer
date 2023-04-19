package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.*;

@RunWith(Suite.class)
@SuiteClasses({
AddUserTest.class,
LoginAsNewUserTest.class,
AddNewArticleTest.class,
EditArticleTest.class,
AddCategoryTest.class,
AssignCategoryTest.class,
ChangePasswordTest.class,
AddMenuItemTest.class,
ArchiveArticleTest.class,
SeeArchivedArticleTest.class,
AddGroupTest.class,
AssignUserToGroupTest.class,
AddFieldTest.class,
AddFieldGroupTest.class,
AssignFieldToGroupTest.class,
DeleteArticleTest.class,
DeleteUserGroupTest.class,
DeleteFieldGroupTest.class,
DeleteFieldTest.class,
DeleteUserTest.class,
DeleteCategoryTest.class

		
})

public class TestSuite {
}
