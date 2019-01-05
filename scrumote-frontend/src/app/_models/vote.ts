export class Vote {

  iteration: number;
  value: string;
  userId: number | null;

  constructor(iteration: number, value: string, userId: number | null) {
    this.iteration = iteration;
    this.value = value;
    this.userId = userId;
  }

  static create(iteration: number, value: string) {
    return new Vote(iteration, value, null);
  }
}
