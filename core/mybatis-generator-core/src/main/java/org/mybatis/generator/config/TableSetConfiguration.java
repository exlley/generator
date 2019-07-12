/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
//  =============================================================================
//  Copyright (c) 2016~2019 Xi'an Linggu Software Co.Ltd. All rights reserved.
//  mybatis-generator -- 2019-07-12 12:46
//  =============================================================================
package org.mybatis.generator.config;

/**
 * @author resky
 * @version 1.0
 * @date 2019-07-12
 */
public class TableSetConfiguration {
    private boolean suffixAsPackage;
    public static String ATTR_SUFFIX_AS_PACKAGE = "suffixAsPackage";
    public static String SUFFIX_AS_PACKAGE_VALUE_TRUE = "true";
    public boolean isSuffixAsPackage() {
        return suffixAsPackage;
    }

    public void setSuffixAsPackage(boolean suffixAsPackage) {
        this.suffixAsPackage = suffixAsPackage;
    }
}
