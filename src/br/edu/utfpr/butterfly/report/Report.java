package br.edu.utfpr.butterfly.report;

public class Report {

    private String name;
    private Integer testAdditionAmount = 0;
    private Integer testFirstAmount = 0;
    private Integer testLastAmount = 0;
    private Integer testDrivenDevelopmentAmount = 0;
    private Integer unknownAmount = 0;
    private Integer refactoringAmount = 0;

    public Report(String name) {
        this.name = name;
    }

    public void addTestAddition() {
        testAdditionAmount++;
    }

    public void addTestFirst() {
        testFirstAmount++;
    }

    public void addTestLast() {
        testLastAmount++;
    }

    public void addUnknown() {
        unknownAmount++;
    }

    public void addTestDrivenDevelopment() {
        testDrivenDevelopmentAmount++;
    }

    public void addRefactoring() {
        refactoringAmount++;
    }

    public Integer getTestAdditionAmount() {
        return testAdditionAmount;
    }

    public Integer getTestFirstAmount() {
        return testFirstAmount;
    }

    public Integer getTestLastAmount() {
        return testLastAmount;
    }

    public Integer getTestDrivenDevelopmentAmount() {
        return testDrivenDevelopmentAmount;
    }

    public Integer getUnknownAmount() {
        return unknownAmount;
    }

    public Integer getRefactoringAmount() {
        return refactoringAmount;
    }
    
    private Integer getTotal() {
        return testAdditionAmount + testFirstAmount + testLastAmount + testDrivenDevelopmentAmount + unknownAmount
                + refactoringAmount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Report [name=");
        builder.append(name);
        builder.append(", testAdditionAmount=");
        builder.append(testAdditionAmount);
        builder.append(", testFirstAmount=");
        builder.append(testFirstAmount);
        builder.append(", testLastAmount=");
        builder.append(testLastAmount);
        builder.append(", testDrivenDevelopmentAmount=");
        builder.append(testDrivenDevelopmentAmount);
        builder.append(", unknownAmount=");
        builder.append(unknownAmount);
        builder.append(", refactoringAmount=");
        builder.append(refactoringAmount);
        builder.append(", total=");
        builder.append(getTotal());
        builder.append("]");
        return builder.toString();
    }

}
