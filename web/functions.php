<?php
// List of supported API calls.
// Each item corresponds with a file in the functions/ folder, containing a function accepting the specified parameters.
$functions = array(
    'match' => array(
        'params' => array(
            'user_id' => array(
                'required' => true,
                'type' => 'uuid',
            ),
            'use_location' => array(
                'required' => false,
                'default' => false,
                'type' => 'boolean',
            ),
            'use_games' => array(
                'required' => false,
                'default' => false,
                'type' => 'boolean',
            ),
        )
    )
);

?>