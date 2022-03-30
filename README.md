# Stockle

Stockle is a game inspired by Wordle where you guess a random S&P 500 stock ticker. The application compares your guess to the correct answer based on several attributes, including sector, industry, HQ location, number of characters in the ticker, year founded, and one year return. You have six tries to improve and guess the correct answer.

# Use

Run stockle.sh to start a game after cloning the repository:

```
git clone https://github.com/CSE237SP2022/project-alexanderarnbergbockgoldware.git
cd project-alexanderarnbergbockgoldware
./stockle.sh
```
A game can also be started by running Stockle.java within Eclipse (in case of any terminal compilation/compatability errors).

# Iteration 1

What user stories were completed this iteration?

- A Game should load in all stock data from a CSV file
- A Game should randomly pick a stock ticker to be the correct answer
- A Game should provide playing instructions and prompt the user to guess
- A Guess should be compared to the correct Answer across all categories
- A User should be able to easily play Stockle via a script

What user stories do you intend to complete next iteration?

- Right now, a player can only make a singular guess. In the next iteration, we intend to add functionality for making up to 6 consecutive guesses to work towards the right answer.
- We also intend to style the feedback a user receives about their guess using bold/italics to be easier to interpret.
- Finally, we would like to add functionality for a user to be able to play multiple games and keep track of their winning streak.

Is there anything that you implemented but doesn't currently work?

- Currently, gameplay only allows a singular guess before the game ends, and we will continue developing gameplay in future iterations to allow for multiple consecutive guesses.
- Additionally, our script to start a game (stockle.sh) doesn't correctly execute for one team member who is using Windows, so in the next iteration we will continue to explore this compatability issue and ensure that Stockle functions correctly on all operating systems. If the script currently poses any issue during testing/grading, the game can also be initiated by running Stockle.java in Eclipse.

What commands are needed to compile and run your code from the command line (or better yet, provide a script that people can use to run your program!)

```
git clone https://github.com/CSE237SP2022/project-alexanderarnbergbockgoldware.git
cd project-alexanderarnbergbockgoldware
./stockle.sh
```
A game can also be started by running Stockle.java within Eclipse (in case of any terminal compilation/compatability errors).

## References

ArgsProcessor code utilized in CSE131 is included to provide an interface for users to input guesses.
All other code references are included via inline comments.
