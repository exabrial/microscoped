/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tomitribe.microscoped.method;

import org.tomitribe.microscoped.core.ScopeContext;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

@Interceptor
@MethodScopeEnabled
public class MethodScopedInterceptor {

    @Inject
    private BeanManager beanManager;

    @AroundInvoke
    public Object invoke(InvocationContext invocation) throws Exception {
        final ScopeContext<Method> context = (ScopeContext<Method>) beanManager.getContext(MethodScoped.class);

        final Method previous = context.enter(invocation.getMethod());
        try {
            return invocation.proceed();
        } finally {
            context.exit(previous);
        }
    }
}
