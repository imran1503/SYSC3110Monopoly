/**
 * Provides user input to the Board model when it is in the middle of performing actions
 * (armies to attack/defend with, direction to fortify, etc.)
 */
public interface boardInput {
    /**
     * Returns a string input from the user
     * @param prompt The prompt message
     * @param defaultValue The default value
     * @return The input string
     */
    public String getStringInput(String prompt, String defaultValue);
    /**
     * Returns a integer input from the user with inclusive minimum and maximum values
     * @param prompt The prompt message
     * @param min The minimum value
     * @param max The maximum value
     * @return The input integer
     */
    public int getIntInput(String prompt, int min, int max);

    /**
     * Returns an integer representing a user chosen option from a list of options
     * @param prompt The prompt message
     * @param options The list of options
     * @return An integer representing a user chosen option from the list of options
     */
    public int getOption(String prompt, Object[] options);

    boardInput getStringInput(String s);
}