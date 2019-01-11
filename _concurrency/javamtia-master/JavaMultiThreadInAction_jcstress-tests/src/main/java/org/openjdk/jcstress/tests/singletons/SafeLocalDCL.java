/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jcstress.tests.singletons;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressMeta;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.function.Supplier;

/**
 * Tests the safe double-checked locking singleton.
 *
 * @author Aleksey Shipilev (aleksey.shipilev@oracle.com)
 */
public class SafeLocalDCL {

    @JCStressTest
    @JCStressMeta(GradingSafe.class)
    public static class Unsafe {
        @Actor
        public final void actor1(SafeLocalDCLFactory s) {
            s.getInstance(SingletonUnsafe::new);
        }

        @Actor
        public final void actor2(SafeLocalDCLFactory s, IntResult1 r) {
            r.r1 = Singleton.map(s.getInstance(SingletonUnsafe::new));
        }
    }

    @JCStressTest
    @JCStressMeta(GradingSafe.class)
    public static class Safe {
        @Actor
        public final void actor1(SafeLocalDCLFactory s) {
            s.getInstance(SingletonSafe::new);
        }

        @Actor
        public final void actor2(SafeLocalDCLFactory s, IntResult1 r) {
            r.r1 = Singleton.map(s.getInstance(SingletonSafe::new));
        }
    }

    @State
    public static class SafeLocalDCLFactory {
        private volatile Singleton instance;

        public Singleton getInstance(Supplier<Singleton> s) {
            Singleton i = instance;
            if (i == null) {
                synchronized (this) {
                    i = instance;
                    if (i == null) {
                        i = s.get();
                        instance = i;
                    }
                }
            }
            return i;
        }
    }

}