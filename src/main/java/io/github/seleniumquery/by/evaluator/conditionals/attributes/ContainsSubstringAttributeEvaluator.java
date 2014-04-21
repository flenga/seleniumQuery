package io.github.seleniumquery.by.evaluator.conditionals.attributes;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import io.github.seleniumquery.by.evaluator.CSSCondition;
import io.github.seleniumquery.by.evaluator.SelectorUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Selector;

public class ContainsSubstringAttributeEvaluator implements CSSCondition<AttributeCondition> {

	private static final ContainsSubstringAttributeEvaluator instance = new ContainsSubstringAttributeEvaluator();

	public static ContainsSubstringAttributeEvaluator getInstance() {
		return instance;
	}
	
	private ContainsSubstringAttributeEvaluator() { }

	/**
	 * Currently it is (mistakenly?) mapped to the type {@link org.w3c.css.sac.Condition#SAC_ATTRIBUTE_CONDITION}.
	 * The factory then inspects the actual type and redirects here.
	 * 
	 * This selector is:
	 * [attribute*=stringToStart]
	 * 
	 * CASE INsensitive!
	 */
	@Override
	public boolean is(WebDriver driver, WebElement element, Selector selectorUpToThisPoint, AttributeCondition attributeCondition) {
		String attributeName = attributeCondition.getLocalName();
		if (!SelectorUtils.hasAttribute(element, attributeName)) {
			return false;
		}
		String wantedValue = attributeCondition.getValue();
		String attributeValue = element.getAttribute(attributeName);
		return containsIgnoreCase(attributeValue, wantedValue);
	}

}