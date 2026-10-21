public class SignupForm {

    @NotBlank
    @MaxLength(20)
    String username;

    @NotBlank
    @MaxLength(50)
    String email;

    public SignupForm(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
