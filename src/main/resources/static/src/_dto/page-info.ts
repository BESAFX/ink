exportclassPageInfo {
  count:number = 0;
  limit:number = 0;
  offset:number = 0;
  pageSize:number = 0;

  constructor(offset: number, pageSize: number) {
        this.offset = offset;
        this.pageSize = pageSize;
    }
}
