/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.proofpoint.bootcamp;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.proofpoint.bootcamp.monitor.ContactEvent;
import com.proofpoint.bootcamp.monitor.ContactMonitor;
import com.proofpoint.bootcamp.monitor.ContactStats;
import org.weakref.jmx.guice.MBeanModule;

import static com.proofpoint.event.client.EventBinder.eventBinder;

public class MainModule
        implements Module
{
    public void configure(Binder binder)
    {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();

        binder.bind(ContactStore.class).in(Scopes.SINGLETON);
        binder.bind(ContactResource.class).in(Scopes.SINGLETON);
        binder.bind(ContactStats.class).in(Scopes.SINGLETON);
        binder.bind(ContactMonitor.class).in(Scopes.SINGLETON);

        MBeanModule.newExporter(binder).export(ContactResource.class).withGeneratedName();

        eventBinder(binder).bindEventClient(ContactEvent.class);
    }
}
