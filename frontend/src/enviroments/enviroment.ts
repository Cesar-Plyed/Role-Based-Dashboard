export const enviroment = {
  production: false,
  apiUrl: 'http://localhost:8080/api',
  authEndpoints: {
    login: '/auth/login',
    register: '/auth/register',
  },
  productsEndpoints: {
    products: '/products',
  },
  cartEndpoints: {
    userCart: (userId: string | number) => `/cart/${userId}`,
    addItemsInUserCart: (userId: string | number) => `/cart/${userId}/items`,
    removeUserItem: (userId: string | number, cartItem: string | number) =>
      `/cart/${userId}/items/${cartItem}`,
  },
};
