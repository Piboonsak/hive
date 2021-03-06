/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.hooks;

import org.apache.hadoop.hive.conf.HiveConf;

import java.util.List;

public class HookUtils {

  public static String redactLogString(HiveConf conf, String logString) {

    String redactedString = logString;

    if (conf != null && logString != null) {
      List<Redactor> queryRedactors = readHooksFromConf(conf, HookContext.HookType.QUERY_REDACTOR_HOOKS);
      for (Redactor redactor : queryRedactors) {
        redactor.setConf(conf);
        redactedString = redactor.redactQuery(redactedString);
      }
    }
    return redactedString;
  }

  public static <T> List<T> readHooksFromConf(HiveConf conf, HookContext.HookType type) {
    return new HiveHooks(conf).getHooks(type);
  }
}
