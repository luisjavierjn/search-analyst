export class Company {

  constructor(
    public id: string,
    public objective: string,
    public type: string,
    public locations: string[],
    public remote: string,
    public code: string,
    public currency: string,
    public minAmount: number,
    public maxAmount: number,
    public periodicity: string
  ) {  }

}
