package ctf.worldcorp.collaborators;

public class Collaborator {
    public final String name;
    public final String age;
    public final String role;

    public Collaborator(String name, String age, String role) {
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public static Collaborator empty() {
        return new Collaborator("Unknown", "Unknown", "Unknown");
    }
}
