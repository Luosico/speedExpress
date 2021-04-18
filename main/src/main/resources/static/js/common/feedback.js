const Feedback = {
    data() {

        return {
            username: 'luosico',
        }
    },
    methods: {

    }
}

let feedback = Vue.createApp(Feedback);
feedback.use(ElementPlus);
feedback.mount('#feedback');
