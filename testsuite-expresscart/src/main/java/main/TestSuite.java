package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.*;

@RunWith(Suite.class)
@SuiteClasses({
	AddUserTest.class,
	LoginUserTest.class,
	AddExistingUserFailsTest.class,
	AddProductTest.class,
	AddNewProdToCartTest.class,
	SearchProductTest.class,
	AddReviewTest.class,
	SeeReviewTest.class,
	AddDiscountCodeAmountTest.class,
	AddDiscountCodePercentTest.class,
	UseDiscountCodeAmountTest.class,
	UseDiscountCodePercentTest.class,
	AddProductTagTest.class,
	SearchProductTagTest.class,
	AddMenuTest.class,
	OpenMenuTest.class,
	DeleteDiscountCodeAmountTest.class,
	DeleteDiscountCodePercentTest.class,
	DeletedDiscountCodeFailsAmountTest.class,
	DeletedDiscountCodeFailsPercentTest.class,
	DeleteUserTest.class,
	LoginDeletedUserFailsTest.class,
	DeleteReviewTest.class,
	DeleteProductTagTest.class,
	SearchDeletedProductTagFailsTest.class,
	DeleteProductTest.class,
	SearchDeletedProductFailsTest.class
})

public class TestSuite {
}
