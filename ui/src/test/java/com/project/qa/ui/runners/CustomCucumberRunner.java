package com.project.qa.ui.runners;

import com.project.qa.core.listeners.JUnitListeners;
import cucumber.api.junit.Cucumber;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

/**
 * @author : Vikas S.
 * @since : 05-06-2019, Wed
 **/
public class CustomCucumberRunner extends Cucumber {


    public CustomCucumberRunner(Class clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier runNotifier) {
        runNotifier.addListener(new JUnitListeners());
        super.run(runNotifier);
    }
}
