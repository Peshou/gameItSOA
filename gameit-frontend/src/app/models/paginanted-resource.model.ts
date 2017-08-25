import {Deserialization} from "./shared/deserialization.model";
import {isNullOrUndefined} from "util";
export class PaginatedResource<T extends Deserialization> extends Deserialization {
  items: Array<T> = [];
  page: PageDetails;

  static itemsPerPage: number = 20;

  constructor(currentPage?: number, numPages?: number, totalCount?: number, items?: Array<T>) {
    super();
    this.page = new PageDetails();
    this.page.number = currentPage ? currentPage : 0;

    if (numPages) {
      this.page.totalPages = numPages;
    }

    if (totalCount) {
      this.page.totalElements = totalCount;
    }

    if (items) {
      this.items = items;
    }
  }

  append(nextItemList: PaginatedResource<T>) {
    if (nextItemList) {
      this.items = this.items.concat(nextItemList.items);
      this.page.number = nextItemList.page.number;
      this.page.totalPages = nextItemList.page.totalPages;
      this.page.totalElements = nextItemList.page.totalElements;
    }
  }

  nextPage() {
    let nextPage = this.page.number + 1;
    return nextPage <= this.page.totalPages ? nextPage : null;
  }

  deserializeGeneric(json: any, clazz: any) {
    json.content.forEach((item: any) => {
      let deserialized_item = new clazz().deserialize(item);
      this.items.push(deserialized_item);
    });

    if (json.page) {
      if (!isNullOrUndefined(json.page.number)) this.page.number = json.page.number;
      if (!isNullOrUndefined(json.page.totalPages)) this.page.totalPages = json.page.totalPages;
      if (!isNullOrUndefined(json.page.totalElements)) this.page.totalElements = json.page.totalElements;
      if (!isNullOrUndefined(json.page.size)) this.page.size = json.page.size;
    } else {
      if (!isNullOrUndefined(json.number)) this.page.number = json.number;
      if (!isNullOrUndefined(json.totalPages)) this.page.totalPages = json.totalPages;
      if (!isNullOrUndefined(json.totalElements)) this.page.totalElements = json.totalElements;
      if (!isNullOrUndefined(json.size)) this.page.size = json.size;
    }

    return this;
  }

  serialize() {
    return {
      items: this.items.map((item) => {
        return item.serialize();
      })
    }
  }

}
export class PageDetails {
  size: number;
  totalElements: number;
  totalPages: number;
  number: number = 0;
}
