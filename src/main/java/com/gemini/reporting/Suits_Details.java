package com.gemini.reporting;



import com.gemini.utils.GemJarGlobalVar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Suits_Details {
    private String report_type;
    private String s_run_id;
    private long s_start_time;
    private long s_end_time;
    private String status;
    private String project_name;
    private String run_type;
    private String user;
    private String env;
    private String machine;
    private String initiated_by;
    private String run_mode;
    ArrayList<TestCase_Details> TestCase_Details = new ArrayList<TestCase_Details>();
    Testcase_Info Testcase_Info;

    public Suits_Details(String s_run_id, String projectName, String env) {
        this.s_run_id = s_run_id;
        this.project_name = projectName;
        this.env = env;
        this.s_start_time = GemReportingUtility.getCurrentTimeInMilliSecond();
        this.user = GemReportingUtility.getCurrentUserName();
        this.machine = GemReportingUtility.getMachineName();
        this.initiated_by = GemReportingUtility.getCurrentUserName();
        this.run_mode = System.getProperty("os.name");
        this.run_type = "ON DEMAND";
        if (GemJarGlobalVar.report_type != null) {
            this.report_type = GemJarGlobalVar.report_type;
        } else {
            this.report_type = "Automation";
        }
    }

    public void addTestCaseDetail(TestCase_Details testCase_Details) {
        this.TestCase_Details.add(testCase_Details);
    }

    public void addTestCaseInfo() {
        int exe = 0;
        int fail = 0;
        int incomplete = 0;
        int info = 0;
        int pass = 0;
        int req = 0;
        int total = TestCase_Details.size();
        int warn = 0;

        for (TestCase_Details testCaseDetail : TestCase_Details) {
            switch (testCaseDetail.getStatus().toLowerCase()) {
                case "exe":
                    exe += 1;
                    break;
                case "fail":
                    fail += 1;
                    break;
                case "incomplete":
                    incomplete += 1;
                    break;
                case "info":
                    info += 1;
                    break;
                case "pass":
                    pass += 1;
                    break;
                case "req":
                    req += 1;
                    break;
                case "warn":
                    warn += 1;
                    break;
                default:
                    break;
            }
        }

        this.Testcase_Info = new Testcase_Info();
        if (exe > 0) {
            this.Testcase_Info.setEXE(exe);
        }
        this.Testcase_Info.setFAIL(fail);
        if (incomplete > 0) {
            this.Testcase_Info.setINCOMPLETE(incomplete);
        }
        if (info > 0) {
            this.Testcase_Info.setINFO(info);
        }
        this.Testcase_Info.setPASS(pass);
        if (req > 0) {
            this.Testcase_Info.setREQ(req);
        }
        this.Testcase_Info.setTOTAL(total);
        if (warn > 0) {
            this.Testcase_Info.setWARN(warn);
        }
    }

    public void endSuite() {
        addTestCaseInfo();
        setSuiteStatus();

        this.s_end_time = GemReportingUtility.getCurrentTimeInMilliSecond();
    }

    private void setSuiteStatus() {
        Set<String> testCaseStatSet = new HashSet<String>();
        for (TestCase_Details testcase : TestCase_Details) {
            testCaseStatSet.add(testcase.getStatus());
        }
        if (testCaseStatSet.contains(STATUS.FAIL.name())) {
            this.status = STATUS.FAIL.name();
        } else if (testCaseStatSet.contains(STATUS.WARN.name())) {
            this.status = STATUS.WARN.name();
        } else if (testCaseStatSet.contains(STATUS.INCOMPLETE.name())) {
            this.status = STATUS.INCOMPLETE.name();
        } else {
            this.status = STATUS.PASS.name();
        }
    }

    public String getS_run_id() {
        return s_run_id;
    }

    public float getS_start_time() {
        return s_start_time;
    }

    public float getS_end_time() {
        return s_end_time;
    }

    public String getStatus() {
        return status;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getRun_type() {
        return run_type;
    }

    public String get_report_type() {
        return report_type;
    }

    public String getUser() {
        return user;
    }

    public String getEnv() {
        return env;
    }

    public String getMachine() {
        return machine;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public String getRun_mode() {
        return run_mode;
    }

    public Testcase_Info getTestcase_Info() {
        return Testcase_Info;
    }

    // Setter Methods

    public void setS_run_id(String s_run_id) {
        this.s_run_id = s_run_id;
    }

    public void setS_start_time(long s_start_time) {
        this.s_start_time = s_start_time;
    }

    public void setS_end_time(long s_end_time) {
        this.s_end_time = s_end_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public void setRun_type(String run_type) {
        this.run_type = run_type;
    }

    public void set_report_type(String report_type) {
        this.report_type = report_type;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public void setRun_mode(String run_mode) {
        this.run_mode = run_mode;
    }

    public void setTestcase_Info(Testcase_Info Testcase_Info) {
        this.Testcase_Info = Testcase_Info;
    }
}
