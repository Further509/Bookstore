<template>
    <div>
        <Navbar />
        <main>
            <BookList :books="books" />
        </main>
        </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import BookList from '@/components/BookList.vue';
import { fetchBooks } from '@/api/books';

export default {
name: 'Home',
components: {
    Navbar,
    BookList,
},
data() {
    return {
    books: [],
    };
},
async mounted() {
    try {
    this.books = await fetchBooks();
    } catch (error) {
    console.error('Failed to load books:', error);
    }
},
};
</script>

<style scoped>
main {
padding: 2rem;
}
</style>
