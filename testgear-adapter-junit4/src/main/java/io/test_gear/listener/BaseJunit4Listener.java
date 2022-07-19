package io.test_gear.listener;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import io.test_gear.models.ClassContainer;
import io.test_gear.models.ItemStatus;
import io.test_gear.models.MainContainer;
import io.test_gear.models.TestResult;
import io.test_gear.models.*;
import io.test_gear.services.ExecutableTest;
import io.test_gear.services.Adapter;
import io.test_gear.services.AdapterManager;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@RunListener.ThreadSafe
public class BaseJunit4Listener extends RunListener
{
    private final AdapterManager adapterManager;
    private final ThreadLocal<ExecutableTest> executableTest = ThreadLocal.withInitial(ExecutableTest::new);
    private final String launcherUUID = UUID.randomUUID().toString();
    private final String classUUID = UUID.randomUUID().toString();

    public BaseJunit4Listener() {
        adapterManager = Adapter.getAdapterManager();
    }

    @Override
    public void testRunStarted(final Description description) {
        adapterManager.startTests();

        final MainContainer mainContainer = new MainContainer()
                .setUuid(launcherUUID);

        adapterManager.startMainContainer(mainContainer);

        final ClassContainer classContainer = new ClassContainer()
                .setUuid(classUUID);

        adapterManager.startClassContainer(launcherUUID, classContainer);
    }

    @Override
    public void testRunFinished(final Result result) {
        adapterManager.stopClassContainer(classUUID);
        adapterManager.stopMainContainer(launcherUUID);
        adapterManager.stopTests();
    }

    @Override
    public void testStarted(final Description description) {
        ExecutableTest executableTest = this.executableTest.get();
        if (executableTest.isStarted()) {
            executableTest = refreshContext();
        }
        executableTest.setTestStatus();

        final String uuid = executableTest.getUuid();
        startTestCase(description, uuid);

        adapterManager.updateClassContainer(classUUID,
                container -> container.getChildren().add(uuid));
    }

    @Override
    public void testFailure(final Failure failure) {
        ExecutableTest executableTest = this.executableTest.get();
        executableTest.setAfterStatus();
        stopTestCase(executableTest.getUuid(), failure.getException(), ItemStatus.FAILED);
    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        ExecutableTest executableTest = this.executableTest.get();
        executableTest.setAfterStatus();
        stopTestCase(executableTest.getUuid(), failure.getException(), ItemStatus.FAILED);
    }

    @Override
    public void testIgnored(final Description description) {
        ExecutableTest executableTest = this.executableTest.get();
        executableTest.setAfterStatus();
        stopTestCase(executableTest.getUuid(), null, ItemStatus.SKIPPED);
    }

    @Override
    public void testFinished(final Description description) {
        final ExecutableTest executableTest = this.executableTest.get();
        if (executableTest.isAfter()) {
            return;
        }
        executableTest.setAfterStatus();
        adapterManager.updateTestCase(executableTest.getUuid(), setStatus(ItemStatus.PASSED, null));
        adapterManager.stopTestCase(executableTest.getUuid());
    }

    protected void startTestCase(Description method, final String uuid) {
        String fullName =  method.getClassName();
        int index = fullName.lastIndexOf(".");

        final TestResult result = new TestResult()
                .setUuid(uuid)
                .setLabels(Utils.extractLabels(method))
                .setExternalId(Utils.extractExternalID(method))
                .setWorkItemId(Utils.extractWorkItemId(method))
                .setTitle(Utils.extractTitle(method))
                .setName(Utils.extractDisplayName(method))
                .setClassName((index != -1) ? fullName.substring(index + 1) : fullName)
                .setSpaceName((index != -1) ? fullName.substring(0, index) : null)
                .setLinkItems(Utils.extractLinks(method))
                .setDescription(Utils.extractDescription(method));

        adapterManager.scheduleTestCase(result);
        adapterManager.startTestCase(uuid);
    }

    private void stopTestCase(final String uuid, final Throwable throwable, final ItemStatus status) {
        adapterManager.updateTestCase(uuid, setStatus(status, throwable));
        adapterManager.stopTestCase(uuid);
    }

    private Consumer<TestResult> setStatus(final ItemStatus status, final Throwable throwable) {
        return result -> {
            result.setItemStatus(status);
            if (nonNull(throwable)) {
                result.setThrowable(throwable);
            }
        };
    }

    private ExecutableTest refreshContext() {
        executableTest.remove();
        return executableTest.get();
    }
}
