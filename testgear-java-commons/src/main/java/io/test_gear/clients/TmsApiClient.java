package io.test_gear.clients;

import io.test_gear.client.api.AutoTestsApi;
import io.test_gear.client.api.TestRunsApi;
import io.test_gear.client.invoker.ApiException;
import io.test_gear.client.model.*;

import java.util.List;
import java.util.UUID;

public class TmsApiClient implements ApiClient {
    private static final String AUTH_PREFIX = "PrivateToken";
    private static final boolean INCLUDE_STEPS = true;
    private static final boolean INCLUDE_LABELS = true;

    private final TestRunsApi testRunsApi;
    private final AutoTestsApi autoTestsApi;

    public TmsApiClient(ClientConfiguration config) {
        io.test_gear.client.invoker.ApiClient apiClient = new io.test_gear.client.invoker.ApiClient();
        apiClient.setBasePath(config.getUrl());
        apiClient.setApiKeyPrefix(AUTH_PREFIX);
        apiClient.setApiKey(config.getPrivateToken());

        testRunsApi = new TestRunsApi(apiClient);
        autoTestsApi = new AutoTestsApi(apiClient);
    }

    @Override
    public TestRunV2GetModel createTestRun(TestRunV2PostShortModel model) throws ApiException {
        return testRunsApi.createEmpty(model);
    }

    @Override
    public TestRunV2GetModel getTestRun(String uuid) throws ApiException {
        return testRunsApi.getTestRunById(UUID.fromString(uuid));
    }

    @Override
    public void completeTestRun(String uuid) throws ApiException {
        testRunsApi.completeTestRun(UUID.fromString(uuid));
    }

    @Override
    public void updateAutoTest(AutoTestPutModel model) throws ApiException {
        autoTestsApi.updateAutoTest(model);
    }

    @Override
    public String createAutoTest(AutoTestPostModel model) throws ApiException {
        return autoTestsApi.createAutoTest(model).getId().toString();
    }

    @Override
    public AutoTestModel getAutoTestByExternalId(String projectId, String externalId) throws ApiException {
        List<AutoTestModel> tests = autoTestsApi.getAllAutoTests(UUID.fromString(projectId),
                externalId, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                INCLUDE_STEPS, INCLUDE_LABELS, null, null, null, null, null);

        if ((long) tests.size() == 0) {
            return null;
        }

        return tests.get(0);
    }

    @Override
    public void linkAutoTestToWorkItem(String id, String workItemId) throws ApiException {
        autoTestsApi.linkAutoTestToWorkItem(id, new WorkItemIdModel().id(workItemId));
    }

    @Override
    public void sendTestResults(String testRunUuid, List<AutoTestResultsForTestRunModel> models) throws ApiException {
        testRunsApi.setAutoTestResultsForTestRun(UUID.fromString(testRunUuid), models);
    }
}
