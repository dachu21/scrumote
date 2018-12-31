export class UserHistory {

  plannings: number;
  issues: number;
  votes: number;
  firstVotesBelowEstimate: number;
  firstVotesAboveEstimate: number;
  firstVotesEqualEstimate: number;
  averageFirstVoteLevelDifference: number;

  constructor(plannings: number, issues: number, votes: number, firstVotesBelowEstimate: number,
              firstVotesAboveEstimate: number, firstVotesEqualEstimate: number,
              averageFirstVoteLevelDifference: number) {
    this.plannings = plannings;
    this.issues = issues;
    this.votes = votes;
    this.firstVotesBelowEstimate = firstVotesBelowEstimate;
    this.firstVotesAboveEstimate = firstVotesAboveEstimate;
    this.firstVotesEqualEstimate = firstVotesEqualEstimate;
    this.averageFirstVoteLevelDifference = averageFirstVoteLevelDifference;
  }
}
