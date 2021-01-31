<template id="status">
    <app-frame>
    </app-frame>
</template>
<script>


    Vue.component("status", {
        template: "#status",
        data: () => ({
            status: null,
        }),
        created() {
            this.load()
        },
        methods: {
            load: function (event) {
                fetch(`/api/status`)
                    .then(res => res.json())
                    .then(json =>
                        this.status = json
                    )
                    .catch(() => alert("Error while fetching status"));
            }
        }
    });
</script>
