package com.example.scastellanos.javaday;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class TrampolineSchedulerRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return new MyStatement(base);
    }

    public class MyStatement extends Statement {
        private final Statement base;

        @Override
        public void evaluate() throws Throwable {
            try {
                RxJavaPlugins.setComputationSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setIoSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setNewThreadSchedulerHandler(__ -> Schedulers.trampoline());
                RxJavaPlugins.setSingleSchedulerHandler(__ -> Schedulers.trampoline());
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
                base.evaluate();
            } finally {
                RxJavaPlugins.reset();
                RxAndroidPlugins.reset();
            }
        }

        public MyStatement(Statement base) {
            this.base = base;
        }
    }
}