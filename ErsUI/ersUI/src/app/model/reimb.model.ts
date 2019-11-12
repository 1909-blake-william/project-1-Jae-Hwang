

export class Reimb {

    constructor(
        public reimbId = 0,
        public reimbAmount = 0,
        public reimbSubmit = new Date(),
        public reimbResolve = new Date(),
        public reimbDescription = '',
        public reimbAuthor = '',
        public reimbResolver = '',
        public reimbStatus = '',
        public reimbType = ''
    ) {}

}
