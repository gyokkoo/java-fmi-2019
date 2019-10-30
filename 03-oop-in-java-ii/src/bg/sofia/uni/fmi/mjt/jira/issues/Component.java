package bg.sofia.uni.fmi.mjt.jira.issues;

public class Component {

    private String name;
    private String shortName;

    public Component(String name, String shortName) {
        if (name == null || shortName == null) {
            throw new RuntimeException("Argumensts cannot be null");
        }

        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Component other = (Component) obj;
        return this.name.equals(other.name) && this.shortName.equals(other.shortName);
    }
}
