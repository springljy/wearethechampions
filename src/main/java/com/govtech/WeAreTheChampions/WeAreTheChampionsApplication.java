package com.govtech.WeAreTheChampions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.govtech.WeAreTheChampions.entity.Match;
import com.govtech.WeAreTheChampions.entity.Team;
import com.govtech.WeAreTheChampions.service.MatchService;
import com.govtech.WeAreTheChampions.service.TeamService;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories("my.package.base.*")
@ComponentScan(basePackages = { "my.package.base.*" })
@EntityScan("my.package.base.*")
public class WeAreTheChampionsApplication implements CommandLineRunner {

	private TeamService teamService;
	
	private MatchService matchService;

	public static void main(String[] args) {
		SpringApplication.run(WeAreTheChampionsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("*** Welcome to the Football Championship Management System ***\n");
			System.out.println("1: Manage Teams");
			System.out.println("2: Manage Matches");
			System.out.println("3: Exit\n");

			Integer response = 0;
			while (response < 1 || response > 3) {
				System.out.print("> ");
				response = sc.nextInt();
				sc.nextLine(); // Consume newline

				if (response == 1) {
					manageTeams(sc);
				} else if (response == 2) {
					manageMatches(sc);
				} else if (response == 3) {
					System.out.println("Goodbye!");
					return;
				} else {
					System.out.println("Invalid option, please try again!\n");
				}
			}
		}
	}

	private void manageTeams(Scanner sc) {
		while (true) {
			System.out.println("*** Team Management ***\n");
			System.out.println("1: Add Team");
			System.out.println("2: View All Teams");
			System.out.println("3: Go Back\n");

			int response = 0;
			while (response < 1 || response > 3) {
				System.out.print("> ");
				response = sc.nextInt();
				sc.nextLine();

				if (response == 1) {
					addTeam(sc);
				} else if (response == 2) {
					viewAllTeams();
				} else if (response == 3) {
					return;
				} else {
					System.out.println("Invalid option, please try again!\n");
				}
			}
		}
	}

	private void addTeam(Scanner sc) {
		System.out.println("Enter multiple teams (one per line) with the format: <Team Name> <dd/MM> <Group Number>");
		System.out.println("Type 'done' when you're finished:");

		while (true) {
			String input = sc.nextLine().trim();

			if ("done".equalsIgnoreCase(input)) {
				break;
			}

			try {
				String[] teamData = input.split("\\s+");

				if (teamData.length != 3) {
					System.out.println(
							"Invalid input. Please provide the input in the format: <Team Name> <dd/MM> <Group Number>");
					continue;
				}

				String name = teamData[0];

				String dateStr = teamData[1];
				LocalDate registrationDate = LocalDate
						.parse("2024-" + dateStr.substring(3, 5) + "-" + dateStr.substring(0, 2));

				int groupNumber = Integer.parseInt(teamData[2]);

				Team newTeam = Team.builder()
						.name(name)
						.registrationDate(registrationDate)
						.groupNumber(groupNumber)
						.build();

				teamService.addTeam(newTeam);
				System.out.println("Team " + name + " added successfully!");
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use <dd/MM> format.");
			} catch (NumberFormatException e) {
				System.out.println("Invalid group number. Please enter a valid number for the group.");
			} catch (Exception e) {
				System.out.println("Error processing input: " + e.getMessage());
			}
		}

		System.out.println("Team input finished.\n");
	}

	private void viewAllTeams() {
		teamService.getAllTeams().forEach(System.out::println);
	}

	private void manageMatches(Scanner sc) {
		while (true) {
			System.out.println("*** Match Management ***\n");
			System.out.println("1: Add Match");
			System.out.println("2: View All Matches");
			System.out.println("3: Go Back\n");

			int response = 0;
			while (response < 1 || response > 3) {
				System.out.print("> ");
				response = sc.nextInt();
				sc.nextLine(); // Consume newline

				if (response == 1) {
					addMatch(sc);
				} else if (response == 2) {
					viewAllMatches();
				} else if (response == 3) {
					return;
				} else {
					System.out.println("Invalid option, please try again!\n");
				}
			}
		}
	}

	private void addMatch(Scanner sc) {
		System.out.print("Enter Team 1 name> ");
		String team1Name = sc.nextLine().trim();
		Optional<Team> team1Optional = teamService.getTeamByName(team1Name);

		if (!team1Optional.isPresent()) {
			System.out.println("Team 1 does not exist.");
			return;
		}
		Team team1 = team1Optional.get();

		System.out.print("Enter Team 2 name> ");
		String team2Name = sc.nextLine().trim();
		Optional<Team> team2Optional = teamService.getTeamByName(team2Name);

		if (!team2Optional.isPresent()) {
			System.out.println("Team 2 does not exist.");
			return;
		}
		Team team2 = team2Optional.get();

		System.out.print("Enter Team 1 goals> ");
		int team1Goals = sc.nextInt();

		System.out.print("Enter Team 2 goals> ");
		int team2Goals = sc.nextInt();
		sc.nextLine(); 

		Match newMatch = Match.builder()
				.team1(team1)
				.team2(team2)
				.team1Goals(team1Goals)
				.team2Goals(team2Goals) 
				.build();

		matchService.addMatch(newMatch);
		System.out.println("Match added successfully!\n");
	}

	private void viewAllMatches() {
		matchService.getAllMatches().forEach(System.out::println);
	}
}
