package io.github.seleniumquery.by.evaluator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.Selector;

public interface CSSCondition<T extends Condition> {

	boolean is(WebDriver driver, WebElement element, Selector selector, T condtition);
	
}