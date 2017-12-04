/*
 * Copyright (c) 2017 seleniumQuery authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.seleniumquery.wait;

import org.hamcrest.Matcher;

import io.github.seleniumquery.SeleniumQueryObject;
import io.github.seleniumquery.SeleniumQueryWaitAndOrThen;
import io.github.seleniumquery.SeleniumQueryWaitEvaluateUntil;
import io.github.seleniumquery.wait.evaluators.ContainsEvaluator;
import io.github.seleniumquery.wait.evaluators.ContainsIgnoreCaseEvaluator;
import io.github.seleniumquery.wait.evaluators.EqualsEvaluator;
import io.github.seleniumquery.wait.evaluators.Evaluator;
import io.github.seleniumquery.wait.evaluators.MatchesEvaluator;
import io.github.seleniumquery.wait.evaluators.ThatEvaluator;
import io.github.seleniumquery.wait.evaluators.comparison.GreaterThanEvaluator;
import io.github.seleniumquery.wait.evaluators.comparison.LessThanEvaluator;
import io.github.seleniumquery.wait.getters.Getter;

/**
 * Functions used in the waitUntil().
 *
 * @param <T> The type returned by the getter and TYPE OF THE ARGUMENT used in the end function.
 *
 * @author acdcjunior
 * @since 0.9.0
 */
class EvaluateUntil<T> implements SeleniumQueryWaitEvaluateUntil<T> {

	private final FluentFunction fluentFunction;
	private final Getter<T> getter;
	private final SeleniumQueryObject seleniumQueryObject;
    private final FluentBehaviorModifier fluentBehaviorModifier;

    EvaluateUntil(FluentFunction fluentFunction, Getter<T> getter, SeleniumQueryObject seleniumQueryObject) {
        this(fluentFunction, getter, seleniumQueryObject, FluentBehaviorModifier.REGULAR_BEHAVIOR);
	}

	private EvaluateUntil(FluentFunction fluentFunction, Getter<T> getter, SeleniumQueryObject seleniumQueryObject,
                          FluentBehaviorModifier fluentBehaviorModifier) {
		this.fluentFunction = fluentFunction;
		this.getter = getter;
		this.seleniumQueryObject = seleniumQueryObject;
		this.fluentBehaviorModifier = fluentBehaviorModifier;
	}

	@Override
	public SeleniumQueryWaitAndOrThen isEqualTo(T valueToEqual) {
        return andOrThen(new EqualsEvaluator<>(getter), valueToEqual);
	}

    private <V> AndOrThen andOrThen(Evaluator<V> evaluator, V value) {
        return new AndOrThen(fluentFunction.apply(evaluator, value, seleniumQueryObject, this.fluentBehaviorModifier), this.fluentFunction);
    }

    @Override
	public SeleniumQueryWaitAndOrThen contains(String string) {
        return andOrThen(new ContainsEvaluator(getter), string);
	}

    @Override
    public SeleniumQueryWaitAndOrThen containsIgnoreCase(String string) {
        return andOrThen(new ContainsIgnoreCaseEvaluator(getter), string);
    }

	@Override
	public SeleniumQueryWaitAndOrThen matches(String regex) {
        return andOrThen(new MatchesEvaluator(getter), regex);
	}

	@Override
	public SeleniumQueryWaitAndOrThen isGreaterThan(Number valueToCompare) {
        return andOrThen(new GreaterThanEvaluator(getter), valueToCompare);
	}

    @Override
	public SeleniumQueryWaitAndOrThen isLessThan(Number valueToCompare) {
        return andOrThen(new LessThanEvaluator(getter), valueToCompare);
	}

	@Override
	public SeleniumQueryWaitEvaluateUntil<T> not() {
		return new EvaluateUntil<>(fluentFunction, getter, seleniumQueryObject, this.fluentBehaviorModifier.negate());
	}

    @Override
    public SeleniumQueryWaitAndOrThen that(Matcher<T> hamcrestMatcher) {
        return andOrThen(new ThatEvaluator<>(getter), hamcrestMatcher);
    }

}
