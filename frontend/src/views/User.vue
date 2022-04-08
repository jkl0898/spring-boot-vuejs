<template>
    <div class="user">
        <h1>Create User</h1>

        <h3>Just some database interaction...</h3>

        <input type="text" v-model="user.firstName" placeholder="first name">
        <input type="text" v-model="user.lastName" placeholder="last name">

        <button @click="createNewUser()">Create User</button>

        <h1></h1>
        <input type="text" v-model="user.notebookinfo" placeholder="notebook info">
        <button @click="createNoteBook()">Create NoteBook</button>
        <h4>Create Response: {{ cResponse }}</h4>


        <h1></h1>
        <input type="text" v-model="user.namespace" placeholder="user namespace">
        <button @click="getNoteBookInfo()">Get NoteBookInfo</button>
        <h4>Get Response: {{ gResponse }}</h4>


        <div v-if="showResponse"><h6>User created with Id: {{ user.id }}</h6></div>

        <button v-if="showResponse" @click="retrieveUser()">Retrieve user {{user.id}} data from database</button>

        <h4 v-if="showRetrievedUser">Retrieved User {{retrievedUser.firstName}} {{retrievedUser.lastName}}</h4>
    </div>
</template>

<script lang="ts">
    import {defineComponent} from 'vue';
    import api from "../api/backend-api";
    import {AxiosError} from "axios";

    interface State {
        user: {
            id: number
            firstName: string,
            lastName: string,
            notebookinfo: string,
            namespace: string;
        };
        retrievedUser: {
            id: number
            firstName: string,
            lastName: string;
        };
        showResponse: boolean;
        showRetrievedUser: boolean;
        errors: AxiosError[];
        cResponse: string;
        gResponse: string
    }

    export default defineComponent({
        name: 'User',

        data: (): State => {
            return {
                errors: [],
                user: {
                    id: 0,
                    firstName: '',
                    lastName: '',
                    notebookinfo: '',
                    namespace: ''
                },
                showResponse: false,
                retrievedUser: {
                    id: 0,
                    firstName: '',
                    lastName: ''
                },
                showRetrievedUser: false,
                cResponse: '',
                gResponse: ''
            }
        },
        methods: {
            // Fetches posts when the view is created.
            createNewUser() {
                api.createUser(this.user.firstName, this.user.lastName).then(response => {
                    this.user.id = response.data;
                    console.log('Created new User with Id ' + response.data);
                    this.showResponse = true
                })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            retrieveUser() {
                api.getUser(this.user.id).then(response => {
                    // JSON responses are automatically parsed.
                    this.retrievedUser = response.data;
                    this.showRetrievedUser = true
                })
                    .catch((error: AxiosError): void => {
                        this.errors.push(error)
                    })
            },
            createNoteBook() {
                api.createNoteBook(this.user.notebookinfo).then(response => {
                    this.cResponse = response.data
                    console.log('Created Notebook. ' + response.data);
                })
                    .catch(e => {
                        this.errors.push(e)
                    })
            },
            getNoteBookInfo() {
                api.getNoteBookInfo(this.user.namespace).then(response => {
                    this.gResponse = response.data
                    console.log(response.data)
                })
                    .catch((error: AxiosError) => {
                        this.errors.push(error)
                    })
            }
        }
    });
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
