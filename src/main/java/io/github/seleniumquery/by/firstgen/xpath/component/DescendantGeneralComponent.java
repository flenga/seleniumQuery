/*
 * Copyright (c) 2015 seleniumQuery authors
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

package io.github.seleniumquery.by.firstgen.xpath.component;

import io.github.seleniumquery.by.firstgen.xpath.UnsupportedConditionalSelector;

/**
 * cssA cssB -> "//" -> xpathA//xpathB
 */
public class DescendantGeneralComponent extends XPathComponent {

    public static TagComponent combine(TagComponent one, TagComponent other) {
        DescendantGeneralComponent otherCopyWithModifiedType = new DescendantGeneralComponent(other);
        return new TagComponent(one.xPathExpression,
                                ComponentUtils.joinComponents(one.combinatedComponents, otherCopyWithModifiedType),
                                ComponentUtils.joinFilters(one.elementFilterList, otherCopyWithModifiedType));
    }

    private DescendantGeneralComponent(TagComponent other) {
        super(other.xPathExpression, other.combinatedComponents, other.elementFilterList);
    }

    @Override
    public String mergeIntoExpression(String sourceXPathExpression) {
        if ("*".equals(this.xPathExpression)) {
            return sourceXPathExpression + "//*";
        }
        return sourceXPathExpression + "//*[self::" + this.xPathExpression + "]";
    }

    @Override
    public String mergeExpressionAsCondition(String sourceXPathExpression) {
        throw new UnsupportedConditionalSelector("The 'general descendant' (\"a b\") selector is not supported as condition inside other selectors.");
    }

}