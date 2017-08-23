// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  baseApiUrl: "http://localhost:80/my-zuul",
  imageUploadUrl: "http://localhost:80/my-zuul/my-games",
  stripeKey: 'pk_test_i85fUQVFNX8EQUID3i4Knhgp'
};
//  baseApiUrl: "http://192.168.99.100/my-zuul",
