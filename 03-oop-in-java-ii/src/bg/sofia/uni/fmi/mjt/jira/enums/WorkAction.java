package bg.sofia.uni.fmi.mjt.jira.enums;

public enum WorkAction {
    RESEARCH("research"),
    DESIGN("design"),
    IMPLEMENTATION("implementation"),
    TESTS("tests"),
    FIX("fix");

    private String text;

    WorkAction(String text) {
        this.text = text;
    }

    public String getAbbreviation() {
        return text;
    }
}
